import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientInMemoryWebApiModule } from 'angular-in-memory-web-api';
import { MockApiService } from '../service/mock-api.service';
import { UniqueUsernameValidatorDirective } from '../validator/unique-username-validator.directive';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    HttpClientInMemoryWebApiModule.forRoot(MockApiService),
  ],
  declarations: [AppComponent, UniqueUsernameValidatorDirective],
  bootstrap: [AppComponent],
})
export class AppModule {}
