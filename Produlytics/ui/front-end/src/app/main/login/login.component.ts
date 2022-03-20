import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import {ViewEncapsulation} from '@angular/core';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None 
})
export class LoginComponent implements OnInit {

  loginForm = new FormGroup({
    un : new FormControl('', Validators.required),
    pw : new FormControl('', [Validators.required, Validators.minLength(6)]),
    remember: new FormControl('')
  });

  constructor (private formBuilder: FormBuilder, 
    private router: Router, 
    private loginService: LoginAbstractService,
    private cookieService: CookieService)
    { }

  ngOnInit(): void {
    if (this.cookieService.get('PRODULYTICS_RM'))
      this.router.navigate(['/']);
  }

  onSubmit(){
    if (this.loginForm.invalid) {
      return;
    }
    this.loginService.login(this.loginForm.controls['un'].value,
      this.loginForm.controls['pw'].value,
      this.loginForm.controls['remember'].value)
  }
}
