import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) {}

  /*
   * There are many possible ways to implement this function.
   * This example assumes invocation of "get all users" endpoint with filtering by username
   * so it should return one of two responses:
   * - an array with single user if user exists (as there should be max one user with given username)
   * - an empty array if there is no user with given username exists
   */
  userExists(username: string): Observable<boolean> {
    return this.http
      .get<User[]>('api/users', { params: { username } })
      .pipe(map((matchingUsers) => matchingUsers.length > 0));
  }
}
