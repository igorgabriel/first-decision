import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../model/user';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html'
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  constructor(private service: UserService, private router: Router) {}

  ngOnInit(): void {
    this.load();
  }

  load() {
    this.service.getAll().subscribe(data => this.users = data);
  }

  delete(id?: number) {
    if (!id) return;
    this.service.delete(id).subscribe(() => this.load());
  }

  edit(id: number) {
    this.router.navigate(['/users/edit', id]);
  }

  add() {
    this.router.navigate(['/users/add']);
  }
}
