from gpiozero import Robot
import RPi.GPIO as GPIO
import threading
import time

GPIO.setmode(GPIO.BCM)
GPIO.setup(16, GPIO.OUT) #LED
GPIO.setup(25, GPIO.OUT) #부저
GPIO.setup(15, GPIO.IN, pull_up_down=GPIO.PID_DOWN) #버튼 눌리면 on
GPIO.setup(23, GPIO.OUT) # Trig = 23 초음파 신호 전송핀
GPIO.setup(24, GPIO.IN)  # Echo = 24 초음파 수신하는 수신 핀

def led_on(): #led 키는 쓰레드 생성
    try:
        GPIO.output(16, True)

    finally:
        GPIO.output(16, False)
        GPIO.cleanup(16)

ledOn_Thread = threading.Thread(target = led_on)

def buzzer_on():
    try:
        pwm = GPIO.PWM(25, 262)
        pwm.start(50.0)
    finally:
        pwm.stop()
        GPIO.cleanup(25)

buzzerOn_Thread = threading.Thread(target = buzzer_on)

def measure_distance():
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
        
while True:
    timeToEatingPill = False #추후에 블루투스로 앱에서 받아올 예정
    
    if(timeToEatingPill == True): #약 먹을 시간 되면 앱에서 신호 받아서
        motor = Robot(left=(20, 21), right=(19, 26))
        print("약 봉지 내보내기")
        motor.forward(speed = 1)  # 이건 속도 (0~1) 사이의 값으로 설정
        time.sleep(5)  # 예시로 5초 동안 동작하도록 설정
        timeToEatingPill = False # 약 봉지 내보내고 나면 다시 False
        ledOn_Thread.start()
        buzzerOn_Thread.start()
        #초음파로 약 없어진거 감지하면 센서 멈출 예정
        distance = measure_distance()
        while distance < 3: # 약 먹은게 감지되면 센서들 꺼짐 임의로 일단 3으로 지정했지만 추후에 변경 예정
            print("약을 복용하지 않았습니다.")
            distance = measure_distance()
            
        ledOn_Thread.stop()
        buzzerOn_Thread.stop()
