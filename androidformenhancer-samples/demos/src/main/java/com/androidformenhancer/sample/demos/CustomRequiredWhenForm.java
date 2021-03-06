/*
 * Copyright 2012 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.androidformenhancer.sample.demos;

import com.androidformenhancer.annotation.Required;
import com.androidformenhancer.annotation.When;
import com.androidformenhancer.annotation.Widget;

/**
 * @author Soichiro Kashima
 */
public class CustomRequiredWhenForm {

    @Widget(id = R.id.spn_reason, nameResId = R.string.form_custom_required_when_reason)
    public String reason;

    @Widget(id = R.id.textfield_reason_other, nameResId = R.string.form_custom_required_when_reason_other,
            validateAfter = R.id.spn_reason)
    @Required(when = {
        @When(id = R.id.spn_reason, equalsTo = "2")
    })
    public String reasonOther;

}
