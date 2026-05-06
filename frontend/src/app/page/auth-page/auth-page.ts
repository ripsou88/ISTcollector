import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, inject } from '@angular/core';
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
  protected errorMessage: string = '';

  private cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

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
    this.errorMessage = '';
    if (this.formAuth.invalid) {
      this.formAuth.markAllAsTouched();
      return;
    }

    const authRequest: AuthRequest = this.formAuth.getRawValue() as AuthRequest;
    
    this.authService.sub(authRequest).subscribe({
      next: (respSub) => {
      if (respSub.id) {
        this.authService.auth(authRequest).subscribe({
          next:(resp) => {
            if (resp.token) {
              this.authService.token = resp.token;
              this.router.navigate(['/home']);
            }
          },
          error: (err) => {
            this.errorMessage = err.error ?? 'Erreur lors de la connexion';
            this.cdr.detectChanges();
          }
        });
      }
      },
      error: (err) => { // ← c'est ici que tombe le 409
        this.errorMessage = err.error ?? 'Erreur lors de la création du compte.';
        this.cdr.detectChanges();
      }
    });
  }

  public login() {
    this.errorMessage = '';
    if (this.formAuth.invalid) {
      this.formAuth.markAllAsTouched();
      return;
    }

    const authRequest: AuthRequest = this.formAuth.getRawValue() as AuthRequest;

    this.authService.auth(authRequest).subscribe({
      next: (resp) => {
        if (resp.token) {
          this.authService.token = resp.token;
          this.router.navigate(['/home']);
        }
      },
      error: (err) => {
        this.errorMessage = err.error ?? 'Erreur lors de la connexion.';
        this.cdr.detectChanges();
      }
    });
  }
}
