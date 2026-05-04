import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-auth-page',
  imports: [ CommonModule, ReactiveFormsModule ],
  templateUrl: './auth-page.html',
  styleUrl: './auth-page.css',
})
export class AuthPage {

  protected newUser: boolean = true;

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

  public addUser() {}

  public login() {}
}
