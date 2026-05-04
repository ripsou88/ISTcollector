import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AuthRequest } from '../../dto/auth-request';
import { AuthService } from '../../service/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth-page',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './auth-page.html',
  styleUrl: './auth-page.css',
})
export class AuthPage {
  protected newUser: boolean = true;
  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  // FORM
  private formBuilder: FormBuilder = inject(FormBuilder);
  protected formAuth!: FormGroup;
  protected formUsernameCtrl!: FormControl;
  protected formPasswordCtrl!: FormControl;

  ngOnInit(): void {
    // Fabrication du formulaire avec le FormBuilder
    this.formUsernameCtrl = this.formBuilder.control("", Validators.required);
    this.formPasswordCtrl = this.formBuilder.control("", Validators.required);

    this.formAuth = this.formBuilder.group({
      // Description des contrôles du formulaire
      username: this.formUsernameCtrl,
      password: this.formPasswordCtrl
    });
  }

  public addUser() {
    if (this.formAuth.invalid) {
      this.formAuth.markAllAsTouched();
      return;
    }

    const authRequest: AuthRequest = this.formAuth.getRawValue() as AuthRequest;

    this.authService.sub(authRequest).subscribe((respSub) => {
      if (respSub.id) {
        this.authService.auth(authRequest).subscribe((resp) => {
          if (resp.token) {
            this.authService.token = resp.token;
            this.router.navigate(['/home']);
          }
        });
      }
    });
  }

  public login() {
    if (this.formAuth.invalid) {
      this.formAuth.markAllAsTouched();
      return;
    }

    const authRequest: AuthRequest = this.formAuth.getRawValue() as AuthRequest;

    this.authService.auth(authRequest).subscribe((resp) => {
      if (resp.token) {
        this.authService.token = resp.token;
        this.router.navigate(['/home']);
      }
    });
  }
}
