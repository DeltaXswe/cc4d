import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import { LoginService } from 'src/app/model/login/login.service';
import {ViewEncapsulation} from '@angular/core';

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

  constructor(private formBuilder: FormBuilder, private route: ActivatedRoute, private router: Router, private loginService: LoginService) { 
  }

  ngOnInit(): void {
  }

  onSubmit(){
    if (this.loginForm.invalid) {
      return;
    }
    this.loginService.login(this.loginForm.controls['un'].value, this.loginForm.controls['pw'].value);
  }
}
