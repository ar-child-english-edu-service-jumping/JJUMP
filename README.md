<img src=https://github.com/qkralsrl1029/JJUMP/blob/master/Docs/images/logo.png width=1287 height=250>

-----------------

# Who are we?
기존에 글과 그림으로만 영어를 공부했던 어린이들의 학습 패턴에 새로운 방향성을 제시합니다. 실시간 텍스트 인식 기술을 사용하여 영어 도서를 읽는 어린이가 모르는 단어를 카메라에 비추었을 때, 이에 해당하는 삼차원 모델을 증강 현실 공간에서 제공해 줌으로써 학습의 재미와 그 효과를 극대화 시킵니다. 휴대용 전자기기 외에 별도의 장비가 없이 보다 제한이 적은 환경에서도 학습할 수 있는 모바일 어플리케이션 서비스를 구상하였습니다.

# Base Concept
- Project Structure
<img src=https://github.com/qkralsrl1029/JJUMP/blob/master/Docs/images/structure.png width=600 height=308>

- Database Schema
<img src=https://github.com/qkralsrl1029/JJUMP/blob/master/Docs/images/schema.png width=531 height=441>

- SWOT
<img src=https://github.com/qkralsrl1029/JJUMP/blob/master/Docs/images/swot.jpg>

- Cross SWOT
<img src=https://github.com/qkralsrl1029/JJUMP/blob/master/Docs/images/cross_swot.png>

# What we do?

![Result1](https://github.com/qkralsrl1029/JJUMP/blob/master/Docs/images/result1.png) 

![Result2](https://github.com/qkralsrl1029/JJUMP/blob/master/Docs/images/result2.png) 

![Result3](https://github.com/qkralsrl1029/JJUMP/blob/master/Docs/images/result3.png) 

# Code Details

- AR module

```java
arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().

        ModelRenderable.builder()
                .setSource(getApplicationContext(), R.raw.wolf)

                .build()
                .thenAccept(renderable -> modelWolf = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(getApplicationContext(), "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
```

# More Details

[Demo Video](https://www.youtube.com/watch?v=7-OESz2w2TQ)

[User Manual](https://github.com/qkralsrl1029/JJUMP/blob/master/Docs/%EB%A9%94%EB%89%B4%EC%96%BC.pdf)

[Project Report](https://github.com/qkralsrl1029/JJUMP/blob/master/Docs/%EC%B5%9C%EC%A2%85%20%EB%B3%B4%EA%B3%A0%EC%84%9C.pdf)

# Contact Us

[박민기](https://github.com/qkralsrl1029)
- AR 모듈 제작
- 프론트엔드 작업

[박지수](https://github.com/jisoo-o)
- 프로젝트 전체 소스 디자인
- 프론트엔드 작업
- API 연결

[윤석원](https://github.com/jsdysw)
- DB스키마 제작
- 서버 아키텍쳐 
