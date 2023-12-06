# tnk_reward_plus
## 목차

1. [SDK 설정하기](#1-sdk-설정하기)

    * [라이브러리 등록](#라이브러리-등록)
    * [Manifest 설정하기](#manifest-설정하기)
        * [Application ID 설정하기](#application-id-설정하기)
        * [권한 설정](#권한-설정)
        * [Activity tag 추가하기](#activity-tag-추가하기)
    * [Proguard 사용](#proguard-사용)
    * [COPPA 설정](#coppa-설정)

2. [광고 목록 띄우기](#2-광고-목록-띄우기)
    * [유저 식별 값 설정](#유저-식별-값-설정)
    * [광고 목록 띄우기 (Activity)](#광고-목록-띄우기-activity)

## 1. SDK 설정하기

### 라이브러리 등록
TNK SDK는 Maven Central에 배포되어 있습니다.

settings.gradle에 아래와 같이 mavenCentral()가 포함되어있는지 확인합니다.
```gradle
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        
        // 경로를 추가해 주시기 바랍니다.
        maven { url "https://repository.tnkad.net:8443/repository/public/" }
    }
}
rootProject.name = "project_name"
include ':app'
```

만약 settings.gradle에 저 부분이 존재하지 않다면 최상위 Level(Project)의 build.gradle에 maven repository를 추가해주세요.
```gradle
repositories {
    mavenCentral()
    maven { url "https://repository.tnkad.net:8443/repository/public/" }
}
```

tnk 라이브러리를 사용하기 위해 아래의 코드를 App Module의 build.gradle 파일에 추가해주세요.
```gradle
dependencies {
    implementation 'com.tnkfactory:rewardplus:1.01.42'
}
```
### Manifest 설정하기

#### 권한 설정

아래와 같이 권한 사용을 추가합니다.
```xml
<!-- 인터넷 -->
<uses-permission android:name="android.permission.INTERNET" />
<!-- 동영상 광고 재생을 위한 wifi접근 -->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<!-- 광고 아이디 획득 -->
<uses-permission android:name="com.google.android.gms.permission.AD_ID"/>
```

#### Application ID 설정하기
```diff
- application id설정은 manifest에서 설정하는 방법과 source에서 함수를 호출하는 방법 두 가지 방법이 있습니다.
```
Tnk 사이트에서 앱 등록하면 상단에 App ID 가 나타납니다. 이를 AndroidMenifest.xml 파일의 application tag 안에 아래와 같이 설정합니다.
(*your-application-id-from-tnk-site* 부분을 실제 App ID 값으로 변경하세요.)

```xml
<application>

    <meta-data android:name="tnkad_app_id" android:value="your-application-id-from-tnk-site" />

</application>
```

또는 아래와 같은 방법으로 설정 가능합니다.
```kotlin
  TnkRewardPlus.setAppId("your-application-id-from-tnk-site")
```


#### Activity tag 추가하기

광고 목록을 띄우기 위한 Activity를 <activity/>로 아래와 같이 설정합니다.

```xml
<activity android:name="com.tnkfactory.ad.AdWallActivity" android:exported="true" android:screenOrientation="portrait"/>
```

AndroidManifest.xml의 내용 예시
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.tnkfactory.tnkofferer">

    // 인터넷
    <uses-permission android:name="android.permission.INTERNET" />
    // 동영상 광고 재생을 위한 wifi접근
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    // 광고 아이디 획득
    <uses-permission android:name="com.google.android.gms.permission.AD_ID"/>
    // 기타 앱에서 사용하는 권한
    //...
    //...
    
    
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">

        ...
        ...
        <activity android:name="com.tnkfactory.ad.AdWallActivity" android:exported="true" android:screenOrientation="portrait"/>
        ...
        ...
        <!-- App ID -->
        <meta-data
            android:name="tnkad_app_id"
            android:value="30408070-4051-9322-2239-15040708030f" />
        ...
        ...
    </application>
</manifest>
```


### Proguard 사용

Proguard를 사용하실 경우 Proguard 설정내에 아래 내용을 반드시 넣어주세요.

```
-keep class com.tnkfactory.** { *;}
```

### COPPA 설정

COPPA는 [미국 어린이 온라인 개인정보 보호법](https://www.ftc.gov/tips-advice/business-center/privacy-and-security/children's-privacy) 및 관련 법규입니다. 구글 에서는 앱이 13세 미만의 아동을 대상으로 서비스한다면 관련 법률을 준수하도록 하고 있습니다. 연령에 맞는 광고가 보일 수 있도록 아래의 옵션을 설정하시기 바랍니다.

```java
TnkRwdPlus.setCOPPA(true); // ON - 13세 미만 아동을 대상으로 한 서비스 일경우 사용
TnkRwdPlus.setCOPPA(false); // OFF
```

## 2. 광고 목록 띄우기


```diff
- 주의 : 테스트 상태에서는 테스트하는 장비를 개발 장비로 등록하셔야 광고목록이 정상적으로 나타납니다.
```
링크 : [테스트 단말기 등록방법](https://github.com/tnkfactory/android-sdk-rwd/blob/master/reg_test_device.md)

다음과 같은 과정을 통해 광고 목록을 출력 하실 수 있습니다.

1) TNK SDK 초기화

2) 유저 식별값 설정

3) COPPA 설정

4) 광고 목록 출력

광고 목록을 출력하는 Activity의 예제 소스

```kotlin
public class MainActivity extends AppCompatActivity {

    lateinit var rwdPlus: TnkRwdPlus
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 1) TNK SDK 초기화
       rwdPlus = TnkRwdPlus(this@MainActivity)
        
        // 오퍼월 액티비티를 출력합니다.
        binding.btnOfferwall.setOnClickListener {
           // 유저 식별용 id입니다.
           rwdPlus.setUserId("test_user_id")
           // COAAPA 설정 미국 어린이 온라인 개인정보 보호법(https://www.ftc.gov/business-guidance/privacy-security/childrens-privacy)
           // true - 13세 미만 아동을 대상으로 한 서비스 일경우 사용
           // false - OFF
           rwdPlus.setCOPPA(false)
           // 매체 구분용 app id입니다. TNK 홈페이지 로그인 후 아래의 링크에서 확인 가능합니다.
           // APP ID (https://ads.tnkad.net/tnk/ko/ppi.apps.user?action=getAppInfo&app_id=122289)
           rwdPlus.setApplicationId("30408070-4051-9322-2239-15040708030f")
           // shop이용시 사용되는 전화번호 입니다.(해당 값은 옵션값으로 필요 할 경우 설정합니다.)
           rwdPlus.setPhoneNumber("01023456789")
           // 오퍼월 화면을 출력합니다.
           rwdPlus.showOfferwall(this@MainActivity)
        }
    }
}
```
