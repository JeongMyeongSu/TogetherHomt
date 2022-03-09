### 외부 서비스 정보

- Google Teachable Machine
  구글에서 제공하는 머신러닝 학습도구인 티처블 머신을 활용하여 운동 자세를 학습시켰습니다. 학습된 모델을 바탕으로 pose detection을 이용한 모션 인식을 활용하여 자세 인식을 통해 유저가 각 자세를 잘 수행하는지 체크했습니다. 
  자세 별로 학습시킨 값을 바탕으로 유사도를 판단하여 결과를 판단했습니다.

- 사용 방법
  [https://teachablemachine.withgoogle.com/](https://www.youtube.com/redirect?event=video_description&redir_token=QUFFLUhqbUliUWYwdGhJMW1xU1lLeHdYVkhweHNXZHpHUXxBQ3Jtc0tsbGdXYmlHV2VqZHZrSTFFaTBwT1l4UFZiUXlKeGpZMWkwSlF1YkZmOG9vT2pzTFRGcUY0ajI1SlVpUGg5Nmt6UHpncGowc3lYUTZwUzdycFM1WHozR180MkNtV1N1cy0xODF6bWFreFV3a3FIQm5oTQ&q=https%3A%2F%2Fteachablemachine.withgoogle.com%2F)
  회원가입없이 Get Started 로 시작
  동작을 프레임별로 나눠서 class 에 입력해줍니다. 
  
  **카메라 권한 허용** 을 눌러야 영상을 학습시킬 수 있습니다.
  export model을 눌러서 학습 모델을 내보내줍니다.
  내보낸 예시 코드를 html 문서의 body 태그에 넣어줍니다. 

- 주의사항
  URL과 학습모델들의 위치 경로를 맞춰줘야 합니다. 
