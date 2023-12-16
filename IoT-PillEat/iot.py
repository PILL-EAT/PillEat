import json
import asyncio
import websockets
import RPi.GPIO as GPIO
from gpiozero import Robot
import threading
import time

GPIO.setmode(GPIO.BCM)
GPIO.setup(16, GPIO.OUT) # LED
GPIO.setup(25, GPIO.OUT) # 부저
GPIO.setup(15, GPIO.IN, pull_up_down=GPIO.PID_DOWN) # 버튼 눌리면 on
GPIO.setup(23, GPIO.OUT) # Trig = 23 초음파 신호 전송핀
GPIO.setup(24, GPIO.IN)  # Echo = 24 초음파 수신하는 수신 핀

def led_on(): # LED 켜는 함수 생성
    try:
        GPIO.output(16, True)
    finally:
        GPIO.output(16, False)
        GPIO.cleanup(16)
       
ledOn_Thread = threading.Thread(target = led_on) # led_on 스레드 생성

def buzzer_on(): # 부저 울리는 함수 생성
    try:
         pwm = GPIO.pwm(25, 262)
         pwm.start(50.0)
    finally:
        pwm.stop()
        GPIO.cleanup(25)
        
buzzerOn_Thred = threading.Thread(target = buzzer_on) # buzzer_on 스레드 생성

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

async def user_input(ws):
    type_m = "raspberryfinish"
    await ws.send(json.dumps({"type": type_m, "content": "약 복용 완료"}))

async def ws_listener(ws):
    while True:
        message = await ws.recv()
        on_message(message)
        
        data = json.loads(message)
        if data.get("type") == "timeToPill": # 서버를 통해 timeToPill이라는 메세지를 받음
            while True:
                distance = measure_distance()
                motor = Robot(left=(20, 21), right=(19, 26))
                motor.forward(speed = 1)  # 이건 속도 (0~1) 사이의 값으로 설정
                if distance == 1: # 초음파 센서를 이용하여 측정할 예정이며 약통이 정확히 만들어지면 거리 제대로 측정하여 수정
                    motor.stop()
                    print("약 출력 완료")
                    
                ledOn_Thread.start()  # LED 켜기
                buzzerOn_Thred.start()  # 부저 울리기
                
                if distance >= 3:  # 거리가 3 이상이면 약 복용 완료
                    motor.stop()
                    ledOn_Thread.stop()  # LED 멈추기
                    buzzerOn_Thred.stop()  # 부저 멈추기
                    print("약 복용 완료")
                    await user_input(ws)  # 서버에 약 복용 완료 메시지 전송
                    break
                

async def main():
    server_address = "ws://ceprj.gachon.ac.kr:60037/ws"
    async with websockets.connect(server_address) as ws:
        await on_open(ws)

        # 비동기 입력 및 수신을 위한 두 개의 태스크를 생성
        input_task = asyncio.create_task(user_input(ws))
        listener_task = asyncio.create_task(ws_listener(ws))

        # 두 태스크를 기다림
        await asyncio.gather(input_task, listener_task)

if __name__ == "__main__":
    asyncio.run(main())
