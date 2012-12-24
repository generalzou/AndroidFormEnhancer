AndroidFormEnhancer
===

AndroidFormEnhancer is a library for implementing input forms easily in the Android application.
You can use annotations to describe the definitions of the input form briefly,
reduce codes that handle data of the screens in the Activities and Fragments.

Install
===

The 'library' folder is the main library.
Please import it into the Eclipse or other IDEs.


Usage
===

1.  Create POJO class of the input form, define public fields and add annotations to them.

        public class DefaultForm {
            @Required
            @Text(R.id.textfield_name)
            @Order(1)
            public String name;

            @IntType
            @Text(R.id.textfield_age)
            @Order(2)
            public String age;
        }

1.  If you use types other than String, create an entity class which fields has the same names as
    the form class.

        public class DefaultForm {
            public String name;

            public int age;
        }

1.  Write the codes like following to the Activity or Fragment to extract data from the screen,
    validate them and convert types.

        // Extract input data into the POJO class object
        FormHelper<DefaultForm> helper = new FormHelper<DefaultForm>();
        DefaultForm form = helper.extractFormFromView(this, DefaultForm.class);

        // Validate input data
        ValidationManager validationManager = new ValidationManager(this);
        ArrayList<String> errorMessages = validationManager.validate(form);

        if (errorMessages.size() > 0) {
            // Show error messages
            Toast.makeText(
                    this,
                    StringUtils.serialize(errorMessages),
                    Toast.LENGTH_SHORT).show();
        } else {
            // This entity object has clean and converted data
            DefaultEntity entity = helper.createEntityFromForm(DefaultEntity.class);
        }


Validations
===

Following validation classes are available:

1. DatePatternValidator
    * Validates the EditText's value with date format.
    * The target fields must have field in Form class with `@DatePattern`.
    * Date format used in validation is `java.text.DateFormat.SHORT`, which will change with locale.
    * If you want to use custom format, use `DatePattern#value()`.
1. DigitsValidator
    * Validates the EditText's value whether it consists only of digits (which means 0 through 9).
    * The target fields must have field in Form class with `@Digits`.
1. EmailValidator
    * Validates the EditText's value whether it is e-mail format.
    * The target fields must have field in Form class with `@Email`.
    * Pattern to be used in validation is this: `^[\\w-]+(\\.[\\w-]+)*@([\\w][\\w-]*\\.)+[\\w][\\w-]*$`
    * If you want to use custom format, define your format in your style
      using `afeValidatorDefinitions` and `afeCustomEmailPattern`.
1. HiraganaValidator
    * Validates the EditText's value whether it consists only of Japanese Hiragana.
    * The target fields must have field in Form class with `@Hiragana`.
1. IntRangeValidator
    * Validates the EditText's value whether it is in the specified range:
      the minimum value is `IntRange#min()` and the maximum value is `IntRange#max()`.
    * The target fields must have field in Form class with `@IntRange`.
1. IntTypeValidator
    * Validates the EditText's value whether it is a valid integer format.
    * The target fields must have field in Form class with `@IntType`.
1. KatakanaValidator
    * Validates the EditText's value whether it consists only of Japanese Katakana.
    * The target fields must have field in Form class with `@Katakana`.
1. LengthValidator
    * Validates the EditText's value whether it has the specified length.
    * The target fields must have field in Form class with `@Length`.
1. MaxLengthValidator
    * Validates the EditText's value whether its length is less than or equals to
      the specified length.
    * The target fields must have field in Form class with `@MaxLength`.
1. MaxNumOfDigitsValidator
    * Validates the EditText's value whether its length is less than or equals to
      the specified length.
    * The target fields must have field in Form class with `@MaxNumOfDigits`.
    * This validator resembles to the MaxLengthValidator, but this validator
      does not treat as an error if the value includes non-digit character even
      though its length exceeds `MaxNumOfDigits#value()`.
1. MaxValueValidator
    * Validates the EditText's value whether it is less than or equals to
      the specified value.
    * The target fields must have field in Form class with `@MaxValue`.
1. MinValueValidator
    * Validates the EditText's value whether it is more than or equals to
      the specified value.
    * The target fields must have field in Form class with `@MinValue`.
1. MultibyteValidator
    * Validates the EditText's value whether it consists only of multi-byte characters.
    * The target fields must have field in Form class with `@Multibyte`.
1. NumOfDigitsValidator
    * Validates the EditText's value whether its length is the specified length.
    * The target fields must have field in Form class with `@NumOfDigits`.
    * This validator resembles to the LengthValidator, but this validator
      does not treat as an error if the value includes non-digit character even
      though its length does not match `NumOfDigits#value()`.
1. PastDateValidator
    * Validates the EditText's value is valid date format and past date.
    * The target fields must have field in Form class with `@PastDate`.
    * Date format used in validation is `java.text.DateFormat.SHORT`, which will change with locale.
    * If you want to use custom format, use `@DatePattern` annotation together.
      PastDateValidator uses `DatePattern#value()`.
    * If you do not want to assume today as an error, set `PastDate#allowToday` to `true`.
1. RegexValidator
    * Validates the EditText's value whether it matches the specified regular expression.
    * The target fields must have field in Form class with `@Regex`.
    * Regular expression must be specified by `Regex#value()`.
1. RequiredValidator
    * Validates the EditText's value is not `null` nor empty string.
    * The target fields must have field in Form class with `@Required`.
1. SinglebyteValidator
    * Validates the EditText's value whether it consists only of single-byte characters.
    * The target fields must have field in Form class with `@Singlebyte`.


Orders of the validation
===

Orders of the validation for each items are defined by `@Order` annotations.
For example, if you define form like following, the validators validate `name` at first,
then validate `age`.
Note that they are different from the orders in the screen.

    public class DefaultForm {
        @Required
        @Text(R.id.textfield_name)
        @Order(1)
        public String name;

        @IntType
        @Text(R.id.textfield_age)
        @Order(2)
        public String age;
    }


Customizations
===

You can customize the behaviours and messages like following:

1. Stop policy

    Stop policy controls the validators to continue or stop
    when they detected errors.
    For example, if you want to validate all the items and show all the errors,
    you should define your theme like this:

        <style name="YourTheme">
            <item name="afeValidatorDefinitions">@style/YourValidatorDefinitions</item>
        </style>

        <style name="YourValidatorDefinitions" parent="@style/AfeDefaultValidators">
            <item name="afeStopPolicy">continueAll</item>
        </style>

1. Available validations

    You can enable or disable the standard validators.
    You can also add your own validator.
    For example, if you want to use only the RequiredValidator,
    you should define your theme like this:

        <string-array name="your_standard_validators">
            <item>com.androidformenhancer.validator.RequiredValidator</item>
        </string>

        <style name="YourTheme">
            <item name="afeValidatorDefinitions">@style/YourValidatorDefinitions</item>
        </style>

        <style name="YourValidatorDefinitions" parent="@style/AfeDefaultValidators">
            <item name="afeStandardValidators">@array/your_standard_validators</item>
        </style>

1. Validation error messages

    You can override validation error messages.
    For example, if you want to override the error message for the RequiredValidator,
    You should define your theme like this:

        <string name="custom_msg_validation_required">You MUST fill in %1$s!</string>

        <style name="YourTheme">
            <item name="afeValidatorMessages">@style/YourValidatorMessages</item>
        </style>

        <style name="YourValidatorMessages">
            <item name="afeErrorRequired">@string/custom_msg_validation_required</item>
        </style>

    The name of the item used in the error message is the name of the field in Form class.
    If you want to change name, use annotation's `nameResId` attribute.
    For example, if you define field like:

        @Required
        public String firstName;

    the error message will be "firstName is required".
    If you define Form like this:

        @Required(nameResId = R.string.first_name)
        public String firstName;

    and define strings.xml like:

        <string name="first_name">First name</string>

    then the result will be "First name is required".

ProGuard
===

If you want to use ProGuard, edit proguard-project.txt.

1. Keep class name of the Validators. This is always required.

        -keep class com.androidformenhancer.validator.* { <init>(...); }

1. Keep class members (public fields) of the Forms and Entities.
   If you use FormHelper#createEntityFromForm(), this is required.

        -keepclassmembers class com.androidformenhancer.sample.demos.DefaultForm {
          public *;
        }
        -keepclassmembers class com.androidformenhancer.sample.demos.DefaultEntity {
          public *;
        }


Samples
===

* Sample applications using this library are included in the samples folder.


Tests
===

* Test codes for JUnit test are included in the tests folder.


Developed By
===

* Soichiro Kashima - <soichiro.kashima@gmail.com>


License
===

    Copyright 2012 Soichiro Kashima

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

