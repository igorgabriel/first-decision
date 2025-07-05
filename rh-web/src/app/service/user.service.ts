import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { User } from '../model/user';

@Injectable({ providedIn: 'root' })
export class UserService {
  private baseUrl = 'http://localhost:8080/v1/users';
  constructor(private http: HttpClient) {}

  getAll(): Observable<User[]> {
    return this.http
      .get<User[]>(this.baseUrl)
      .pipe(catchError((err) => this.handleError(err)));
  }

  getById(id: number): Observable<User> {
    return this.http
      .get<User>(`${this.baseUrl}/${id}`)
      .pipe(catchError((err) => this.handleError(err)));
  }

  create(user: User): Observable<User> {
    console.log('Creating user:', user);
    return this.http
      .post<User>(this.baseUrl, user)
      .pipe(catchError((err) => this.handleError(err)));
  }

  update(id: number, user: User): Observable<User> {
    return this.http
      .put<User>(`${this.baseUrl}/${id}`, user)
      .pipe(catchError((err) => this.handleError(err)));
  }

  delete(id: number): Observable<void> {
    return this.http
      .delete<void>(`${this.baseUrl}/${id}`)
      .pipe(catchError((err) => this.handleError(err)));
  }

  private handleError(error: any) {
    console.error('API error', error);
    return throwError(() => new Error(error.message || 'Server error'));
  }
}
