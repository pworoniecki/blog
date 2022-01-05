import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, NgForm, Validators } from '@angular/forms';
import { FormBuilder } from '@angular/forms';
import { MockApiService } from '../service/mock-api.service';
import { UniqueUsernameValidator } from '../validator/unique-username.validator';

@Component({
  selector: 'my-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [UniqueUsernameValidator],
})
export class AppComponent implements OnInit {
  registrationForm: FormGroup;

  @ViewChild('registrationTemplateDrivenForm')
  registrationTemplateDrivenForm: NgForm;

  constructor(
    private fb: FormBuilder,
    private uniqueUsernameValidator: UniqueUsernameValidator
  ) {}

  ngOnInit(): void {
    this.registrationForm = this.fb.group({
      username: [
        '',
        [Validators.required, Validators.minLength(3)],
        [this.uniqueUsernameValidator],
      ],
    });
  }

  getExistingUsers(): string[] {
    return MockApiService.EXISTING_USERS;
  }

  getReactiveFormErrors(): string {
    return JSON.stringify(this.registrationForm.controls['username'].errors);
  }

  getTemplateDrivenFormErrors(): string {
    if (this.registrationTemplateDrivenForm?.form?.controls['username']) {
      return JSON.stringify(
        this.registrationTemplateDrivenForm.form.controls['username'].errors
      );
    } else {
      return null;
    }
  }
}
