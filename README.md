# DualProgressView

[ ![Download](https://api.bintray.com/packages/sreekumar/Artifact/dualprogress/images/download.svg) ](https://bintray.com/sreekumar/Artifact/dualprogress/_latestVersion)
#### How to use

Add below to your root `build.gradle` file

```java
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url "https://dl.bintray.com/sreekumar/Artifact"  -> this one
        }
    }
}

```
then in your module dependency 

```java
 implementation 'com.pollux:dualprogressview:0.0.1'
```java


```xml
<com.pollux.widget.DualProgressView
       android:layout_width="32dp"
       android:layout_height="32dp"/>
```

#### Want more ?

```xml
<com.pollux.widget.DualProgressView
       android:layout_width="32dp"
       android:layout_height="32dp"
       app:dpv_inner_color="@color/colorPrimaryDark"
       app:dpv_inner_padding="8dp"
       app:dpv_outer_color="@color/colorAccent"
       app:dpv_thickness="2dp" />

```
If you want to know in detail about this animation, read it here https://medium.com/@sreekumar_av/how-to-create-your-own-progressbar-in-android-511419293158
