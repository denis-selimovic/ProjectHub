import { AbstractControl } from '@angular/forms';
    
export function FieldsMatch(controlName: string, matchingControlName: string){
    return (formGroup: AbstractControl) => {
        const control = formGroup.get(controlName);
        const matchingControl = formGroup.get(matchingControlName);
        if (matchingControl?.errors && !matchingControl.errors.mustMatch) {
            return;
        }
        if (control?.value !== matchingControl?.value) {
            matchingControl?.setErrors({ mustMatch: true });
        } else {
            matchingControl?.setErrors(null);
        }
    }
}