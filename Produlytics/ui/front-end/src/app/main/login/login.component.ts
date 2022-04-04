import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import { ViewEncapsulation } from '@angular/core';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';
import { CookieService } from 'ngx-cookie-service';
import { LoginCommand } from 'src/app/model/login/login-command';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None 
})
export class LoginComponent implements OnInit {

  /* loginForm = new FormGroup({
    un : new FormControl('', Validators.required),
    pw : new FormControl('', [Validators.required, Validators.minLength(6)]),
    remember: new FormControl('')
  }); */
  loginForm: FormGroup;

  constructor (private formBuilder: FormBuilder, 
    private router: Router, 
    private loginService: LoginAbstractService,
    private cookieService: CookieService){ 
      this.loginForm = this.formBuilder.group({
        username: ['', Validators.required],
        password: ['', [Validators.required, Validators.minLength(6)]],
        rememberMe: ['']
      });
    }

  ngOnInit(): void {
    if (this.cookieService.get('PRODULYTICS_RM'))
      this.router.navigate(['/']);
  }

  onSubmit(){
    const rawValue = this.loginForm.getRawValue();
    const command: LoginCommand = {
      username: rawValue.username,
      password: rawValue.password,
      rememberMe: rawValue.rememberMe
    }  
    if (this.loginForm.invalid) {
      return;
    }
    this.loginService.login(command)
  }
}
