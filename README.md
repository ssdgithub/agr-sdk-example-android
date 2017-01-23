# agr-sdk-example-android
AGR.uz sdk for android

## What is it


## How to use
**Your project's minimum deployment target should be Android 4.0 (API level 14) or later.**

* Add `agrsdk.aar` library to `.../app/libs` folder
* Add the code below to module-level 'build.gradle' file
```
dependencies {
  compile(name:'agrsdk', ext:'aar')
}
```
* Add the code below to project-level build.gradle file
```
allprojects {
  repositories {
    jcenter()
      flatDir{
        dirs 'libs'
       }
    }
}
```
To setup your project you should implement `IAGRBillingHandler` interface and than call `new AGRBilling().preparePayment(IAGRBillingHandler mListener, int vendorID, String accountID, int amount)` where:
* mListener - delegate class for IAGRBillingHandler
* vendorID - your vendor ID from AGR.uz
* vendorSecretKey - your vendor secret key from AGR.uz
* accountID - account indetifier in your service
* amount - amount to pay (optional)

```java
public class MainActivity extends AppCompatActivity implements IAGRBillingHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
				
				//new AGRBilling().preparePayment(this, 5, "7@KXkAYC8-jfJB6VVBFY!kvM?", "member@gmail.com");
        new AGRBilling().preparePayment(this, 5, "7@KXkAYC8-jfJB6VVBFY!kvM?", "member@gmail.com", 5000);
    }

   	@Override
    public void onAGRPaymentSuccess() {
        Toast.makeText(this, "onAGRPaymentSuccess", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAGRPaymentFailed(String message) {
        Toast.makeText(this, "onAGRPaymentFailed " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAGRPaymentCanceled() {
        Toast.makeText(this, "onAGRPaymentCanceled by user", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
```
