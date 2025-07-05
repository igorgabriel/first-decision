import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserListComponent } from './component/user-list/user-list.component';
import { UserFormComponent } from './component/user-form/user-form.component'

const routes: Routes = [
  { path: '', redirectTo: '/users', pathMatch: 'full' },
  { path: 'users', component: UserListComponent },
  { path: 'users/add', component: UserFormComponent },
  { path: 'users/edit/:id', component: UserFormComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
