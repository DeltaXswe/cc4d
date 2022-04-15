import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import { ViewEncapsulation } from '@angular/core';
import { LoginAbstractService } from '../../model/login/login-abstract.service';
import { CookieService } from 'ngx-cookie-service';
import { LoginCommand } from '../../model/login/login-command';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None 
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor (formBuilder: FormBuilder, 
    private router: Router, 
    private loginService: LoginAbstractService,
    private cookieService: CookieService,
    private matSnackBar: MatSnackBar){ 
      this.loginForm = formBuilder.group({
        username: new FormControl('', Validators.required),
        password: new FormControl('', [Validators.required, Validators.minLength(6)]),
        rememberMe: new FormControl ('')
      });
    }

  ngOnInit(): void {
    if (this.cookieService.get('PRODULYTICS_RM'))
      this.router.navigate(['/']);
  }

  onSubmit(): void{
    const rawValue = this.loginForm.getRawValue();
    const command: LoginCommand = {
      username: rawValue.username,
      password: rawValue.password,
      rememberMe: rawValue.rememberMe
    }  
    console.log(command.password);
    if (this.loginForm.invalid) {
      return;
    }
    this.loginService.login(command)
      .subscribe({
        next: () => this.router.navigate(['/']),
        error: () => this.matSnackBar.open('Credenziali non valide', 'Undo', {
          duration: 3000
        })});
  }
}
