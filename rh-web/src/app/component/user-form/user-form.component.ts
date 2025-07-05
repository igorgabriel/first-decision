import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../service/user.service';
import { User } from '../../model/user';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
})
export class UserFormComponent implements OnInit {
  form!: FormGroup;
  id?: number;

  constructor(
    private fb: FormBuilder,
    private service: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.buildForm();
    if (this.id) this.loadData();
  }

  buildForm() {
    this.form = this.fb.group(
      {
        name: ['', [Validators.required, Validators.minLength(3)]],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        passwordConfirmation: ['', [Validators.required]],
      },
      { validators: this.passwordMatchValidator }
    );
  }

  private passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const senha = control.get('password')?.value;
    const confirm = control.get('passwordConfirmation')?.value;
    return senha === confirm ? null : { passwordsMismatch: true };
  }

  loadData() {
    this.service.getById(this.id!).subscribe((user: User) => {
      this.form.patchValue({
        name: user.name,
        email: user.email,
      });
      // não popular senha por segurança
    });
  }

  submit() {
    if (this.form.invalid) return;
    const user: User = this.form.value;
    if (this.id) {
      this.service
        .update(this.id, user)
        .subscribe(() => this.router.navigate(['/users']));
    } else {
      this.service
        .create(user)
        .subscribe(() => this.router.navigate(['/users']));
    }
  }
}
