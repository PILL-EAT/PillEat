import json
import asyncio
import websockets
import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BCM)
GPIO.setup(16, GPIO.OUT)

def on_message(message):
    print(f"Received message: {message}")

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
        if data.get("type") == "timeToPill":
            print("led_on")
            GPIO.output(16, True)

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
