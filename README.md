# TODO_1
![뷰](https://lh3.googleusercontent.com/XTxNCHCLnIuSCtHdWSabSdS9E6nt6462wd5PYZEFUzW_94tOK8qiP9Nvt-HOcwW2-jT3trKSxoEf)

![클](https://lh3.googleusercontent.com/RwFHZcMWUenoXBmW0AUPC1KcT8WsiC3nI5XRqLJGO1hr2g3ysB4-ZXGOQATmj77QpJSHSLV5qaWk "아티")

위 사진은 화면에 표현될 뷰와 그 내부의 아티클이라고 부르며 사용자가
위키에 정보를 저장하는 포맷입니다.
**(프로토는 사진 추가 기능을 구현하지 않도록 합니다.)**
## 설명
다수의 아티클이 추가 될 수 있으므로 아티클 View를 새로 생성할 필요가 있습니다.

이번에 하실일은 아티클 뷰 클래스를 작성하시는 일입니다.

이번에는 이미 파일을 생성하였고 수정만 하므로 따로 branch작업을 하실 필요가 없습니다.

## 요구사항
내부에 있는 뷰는 제목을 나타내는 TextView,
편집화면으로 이동하는 Button
그리고 설명을 나타내는 TextView 로 구성되도록 합니다.


커스텀 뷰를 작성하는 방법은 [여기](http://gun0912.tistory.com/38)를 참고해주세요
**커스텀 뷰를 제작할 때 attr.xml과 관련된건 무시해주셔도 됩니다.**

이해가 안되는 부분은 언제든지 물어주시기 바랍니다.

TODO :
Avtivity/StoreView/ArticleView.class 완성

layout/article_view.xml 완성

기한 : 1월 3일 정오까지


**주의! 새로운 파일을 생성하시면 안됩니다.**

# TODO_2 for gowls4023
## 설명
위의 실제 뷰 사진의 예시를 보시면 아시겠지만 현재 동적으로 추가되는 내용은 아티클(제목 + 내용)과 메뉴가 있습니다.

따라서 메뉴를 위한 포맷도 생각해야합니다.


![enter image description here](https://lh3.googleusercontent.com/lRO84sZOuLTy7K1-SuAK15tET052L2-s_ee6jDgTrRKg-5TtGnVO_cBLRbK2oKyFLKFMFWNYn2JS "메뉴 리스트")

메뉴 부분은 다음과 같이 표시되며 메뉴 리스트는 메뉴가 하나 이상 존재할 경우 표시하도록 합니다.

이 때 동적으로 추가할 부분은

![뷰](https://lh3.googleusercontent.com/LYS8cnEJmjyb2rGMw7EVQeloY28Vf5s9VBeJlsbVGnuF8eopXYf4k5YHIH-Z4FRUDx5Sx10zlQiN "메뉴")

하나의 메뉴를 나타내는 부분입니다.

이번에 하실일은 동적으로 추가하기 위해서 커스텀 메뉴 뷰를 제작하는 것입니다.

이번에는 이미 파일을 생성하였고 수정만 하므로 따로 branch작업을 하실 필요가 없습니다.

## 요구사항
이는
메뉴사진을 나타내는 ImageView,
메뉴이름은 나타내는 TextView,
메뉴 가격을 나타내는 TextView,로 구성되도록 합니다.

커스텀 뷰를 작성하는 방법은 [여기](http://gun0912.tistory.com/38)를 참고해주세요
**커스텀 뷰를 제작할 때 attr.xml과 관련된건 무시해주셔도 됩니다.**

이해가 안되는 부분은 언제든지 물어주시기 바랍니다.

TODO :
Avtivity/StoreView/MenuView.class 완성

layout/menu_view.xml 완성

기한 : 1월 3일 정오까지

**주의! 새로운 파일을 생성하시면 안됩니다.**