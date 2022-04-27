import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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

/**
 * Questo component permette di effettuare il login.
 */
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

  /**
   * Controlla che sia già presente un cookie di sessione.
   * In caso positivo naviga alla dashboard.
   */
  ngOnInit(): void {
    if (this.cookieService.get('PRODULYTICS_RM'))
      this.router.navigate(['/']);
  }

  /**
   * Prende i dati inseriti dall'utente.
   * Se tutti passano le validazioni imposte, effettua un tentativo di login
   * utilizzando un servizio che implementa {@link LoginAbstractService}.
   * In caso di successo porta alla dashboard, altrimenti mostra un errore
   * con {@link MatSnackBar}
   */
  onSubmit(): void{
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
      .subscribe({
        next: () => this.router.navigate(['/']),
        error: () => this.matSnackBar.open('Credenziali non valide', 'Ok', {
          duration: 3000
        })});
  }
}
