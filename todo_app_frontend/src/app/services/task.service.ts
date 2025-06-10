import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Task } from '../models/task';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private url: string = 'http://localhost:8080/api/v1/todo';

  constructor(private http: HttpClient) {}

  findAll(): Observable<Task[]> {
    return this.http
      .get(this.url)
      .pipe(map((response: any) => response as Task[]));
  }

  findById(id: number): Observable<Task> {
    return this.http.get<Task>(`${this.url}/${id}`);
  }

  create(task: Task): Observable<Task> {
    console.log(task);
    return this.http.post<Task>(this.url, task);
  }

  update(task: Task): Observable<Task> {
    return this.http.put<Task>(`${this.url}/${task.id}`, task);
  }

  remove(id: number): Observable<Task> {
    return this.http.delete<Task>(`${this.url}/${id}`);
  }
}
