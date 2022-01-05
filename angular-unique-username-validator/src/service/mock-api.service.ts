import { HttpRequest } from '@angular/common/http';
import {
  InMemoryDbService,
  RequestInfo,
  ResponseOptions,
} from 'angular-in-memory-web-api';
import { Observable } from 'rxjs';
import { User } from '../model/user';

// When any HTTP request is sent from this application, it is redirected here
export class MockApiService implements InMemoryDbService {
  static readonly EXISTING_USERS = ['sheila', 'john', 'kenny'];

  createDb(): {} | Observable<{}> | Promise<{}> {
    return {
      users: MockApiService.EXISTING_USERS.map((username) => ({ username })),
    };
  }

  get(requestInfo: RequestInfo): Observable<User[]> {
    const request: HttpRequest<User[]> = requestInfo.req as HttpRequest<User[]>;
    const username = request.params.get('username');
    const existingUsers: User[] = requestInfo.collection;
    const user: User = existingUsers.find((user) => user.username === username);

    const options: ResponseOptions = {
      headers: requestInfo.headers,
      url: requestInfo.url,
      status: 200,
    };
    if (user) {
      options.body = [user];
    } else {
      options.body = [];
    }
    return requestInfo.utils.createResponse$(() => options);
  }
}
