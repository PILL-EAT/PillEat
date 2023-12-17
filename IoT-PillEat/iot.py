import json
import asyncio
import websockets
import RPi.GPIO as GPIO
from gpiozero import Motor
import time


GPIO.setmode(GPIO.BCM)
GPIO.setup(16, GPIO.OUT) # LED
GPIO.setup(25, GPIO.OUT) # 부저
GPIO.setup(15, GPIO.IN, pull_up_down=GPIO.PUD_DOWN) # 버튼 눌리면 on
GPIO.setup(23, GPIO.OUT) # Trig = 23 초음파 신호 전송핀
GPIO.setup(24, GPIO.IN)  # Echo = 24 초음파 수신하는 수신 핀
motor = Motor(21, 25)

def led_on(): #LED 켜는 함수 생성
    try:
        GPIO.output(16, 1)
        time.sleep(0.5)
    finally:
        GPIO.output(16, False)


def buzzer_on(): #부저 켜는 함수 생성
    try:
        pwm = GPIO.PWM(25, 262)
        pwm.start(50.0)
        time.sleep(0.5)
    finally:
        pwm.stop()

def on_message(message):
    print(f"Received message: {message}")
    
def measure_distance(): # 초음파로 거리 측정하는 함수 생성
    try:
        GPIO.output(23, GPIO.LOW)
        time.sleep(0.1)
        GPIO.output(23, GPIO.HIGH) # trig에서 신호를 내보냄
        time.sleep(0.00001)
        GPIO.output(23, GPIO.LOW)  
        while GPIO.input(24) == 0: # 24번 핀이 OFF 되는 시점을 시작 시간으로 설정
            start = time.time()
            
        while GPIO.input(24) == 1: # 24번 핀이 다시 ON 되는 시점을 수신 시간으로 설정
            stop = time.time()
            
        time_interval = stop - start # 초음파가 수신되는 시간으로 거리를 계산
        m_distance = time_interval * 34300 / 2
        m_distance = round(m_distance, 2)
        return m_distance
    finally:
        pass
    
async def on_open(ws):
    login_message = {
        "type": "raspberryLogin",
        "raspberryId": "2XTV6D"
    }
    await ws.send(json.dumps(login_message))

async def user_input(ws, drug_id, s_type):
    type_m = {
        "type": s_type,
        "raspberryId": "2XTV6D",
        "drugId": drug_id
    }
    await ws.send(json.dumps(type_m))

async def ws_listener(ws):
    while True:
        message = await ws.recv()
        on_message(message)

        data = json.loads(message)
        if data.get("type") == "timeToPill": # 서버를 통해 timeToPill이라는 메세지를 받음
            while True: 
                distance = measure_distance()
                print(distance)
                print("약 내보내기")
                status = "" # 상태 초기화
                motor.forward(speed=0.5) # 이건 속도 (0~1) 사이의 값으로 설정
                
               
                while distance < 10:
                    motor.stop()
                    # 약 내보내고 나면 led 켜진 후 부저 울림
                    led_on()
                    buzzer_on()
                    m_Time = 0
                    while m_Time <= 10: #10분 카운트
                        m_Time += 1
                        led_on()
                        buzzer_on()
                        time.sleep(1)
                        led_on()
                        buzzer_on()
                        print(m_Time)
                        distance = measure_distance()
                        if distance >= 100: # 거리가 100 이상이면 약 복용 완료로 판단
                            drug_Id = data.get("drugId")
                            s_type = "raspberry-finish"
                            print("약 복용 완료")
                            await user_input(ws, drug_Id, s_type)  # 서버에 약 복용 완료 메시지 전송
                            status = "done" # 상태를 done으로 바꿈
                            break
                        else:
                            distance = measure_distance
                            
                    if status == "done": #상태가 done이 되면 while문 탈출
                        break
                    else:
                        drug_Id = data.get("drugId")
                        s_type = "finish-no"
                        print("10분 경과")
                        await user_input(ws, drug_Id, s_type)  # 서버에 약 복용 완료 메시지 전송
                        
                if status == "done":
                    break
                
        if data.get("type") == "takePill": # 약 미리 내보낼 때
            while True:
                print("약 추출")
                distance = measure_distance()
                motor.forward(speed = 0.5)  # 이건 속도 (0~1) 사이의 값으로 설정
                if distance < 10: #거리가 10보댜 작으면 약 출력 완료로 판단 모터 멈춤
                    motor.stop()
                    break
            

async def main():
    server_address = "ws://ceprj.gachon.ac.kr:60037/ws"
    async with websockets.connect(server_address) as ws:
        await on_open(ws)

        listener_task = asyncio.create_task(ws_listener(ws))

        await asyncio.gather(listener_task)
        
        if GPIO.input(15) == GPIO.HIGH: # 버튼이 눌리면 약을 통 안의 심에 감는다
            print("약 감기")
            while True:
                motor.backward(speed = 0.5) # 약이 다 감겼다고 사용자가 생각하여 버튼을 누르면 약 감기 멈춤
                if GPIO.input(15) == GPIO.LOW:
                    motor.stop()
                    break
            
if __name__ == "__main__":
    asyncio.run(main())
