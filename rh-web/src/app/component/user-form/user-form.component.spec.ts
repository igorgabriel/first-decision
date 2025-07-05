import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { UserFormComponent } from './user-form.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('UserFormComponent', () => {
  let component: UserFormComponent;
  let fixture: ComponentFixture<UserFormComponent>;
  let compiled: HTMLElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        RouterTestingModule,
        HttpClientTestingModule, // <<<<< adicionar aqui
      ],
      declarations: [UserFormComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(UserFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    compiled = fixture.nativeElement;
  });

  it('deve desabilitar o botão de submit quando senhas não coincidem', () => {
    const form = component.form;
    form.controls['name'].setValue('Teste');
    form.controls['email'].setValue('test@example.com');
    form.controls['password'].setValue('abc123');
    form.controls['passwordConfirmation'].setValue('xyz789');
    fixture.detectChanges();

    const button = compiled.querySelector(
      'button[type="submit"]'
    ) as HTMLButtonElement;
    expect(form.invalid).toBeTrue();
    expect(button.disabled).toBeTrue();
  });

  it('deve habilitar o botão de submit quando formulário válido', () => {
    const form = component.form;
    form.controls['name'].setValue('Teste');
    form.controls['email'].setValue('test@example.com');
    form.controls['password'].setValue('abc123');
    form.controls['passwordConfirmation'].setValue('abc123');
    fixture.detectChanges();

    const button = compiled.querySelector(
      'button[type="submit"]'
    ) as HTMLButtonElement;
    expect(form.valid).toBeTrue();
    expect(button.disabled).toBeFalse();
  });
});
