import { AbstractControl, FormGroup } from "@angular/forms";

export function ComparePassword(controlName: string, matchingControlName: string) {
    return (control: AbstractControl) => {
        const target = control.get(controlName);
        const match = control.get(matchingControlName);

        if (match?.errors && !match.errors.mustMatch) {
            // return if another validator has already found an error on the matchingControl
            return;
        }

        // set error on matchingControl if validation fails
        if (target?.value !== match?.value) {
            target?.setErrors({ mustMatch: true });
        } else {
            target?.setErrors(null);
        }
    }
}