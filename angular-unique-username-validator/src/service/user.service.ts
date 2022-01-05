import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, first, mapTo, Observable, of, throwError } from 'rxjs';
import { User } from '../model/user';

@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) {}

  /*
   * There are many possible ways to implement this function.
   * This example assumes invocation of "get all users" endpoint with filtering by username
   * so if there is any successful response from backend - it means that user with given username exists,
   * if there is 404 - it means that such user doesn't exist.
   */
  userExists(username: string): Observable<boolean> {
    return this.http.get<User>('api/users', { params: { username } }).pipe(
      mapTo(true),
      catchError((error: HttpErrorResponse) => {
        if (error.status == 404) {
          return of(false);
        } else {
          return throwError(() => error);
        }
      }),
      first()
    );
  }
}
