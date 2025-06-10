import { Component, OnInit } from '@angular/core';
import { FormComponent } from './components/form/form.component';
import { TasksComponent } from './components/tasks/tasks.component';
import { Task } from './models/task';
import { TaskService } from './services/task.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-root',
  imports: [FormComponent, TasksComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  tasks: Task[] = [];
  taskSelected: Task = new Task();

  constructor(private service: TaskService) {}

  ngOnInit(): void {
    this.service.findAll().subscribe((tasks) => (this.tasks = tasks));
  }

  onFilterById(idString: string): void {
    const id = Number(idString);
    console.log('Recibido ID en el padre:', id);

    if (!idString || idString.trim() === '') {
      // Si está vacío, carga todas las tareas
      this.service.findAll().subscribe((tasks) => {
        this.tasks = tasks;
      });
    } else {
      // Si tiene valor, busca por id
      this.service.findById(id).subscribe((task) => {
        this.tasks = task ? [task] : [];
      });
    }
  }

  addTask(task: Task): void {
    if (task.id > 0) {
      this.service.update(task).subscribe({
        next: (taskUpdated) => {
          this.tasks = this.tasks.map((t) =>
            t.id === task.id ? { ...taskUpdated } : t
          );
          Swal.fire({
            toast: true,
            position: 'top-end',
            title: 'Tarea actualizada!',
            icon: 'success',
            showConfirmButton: false,
            timer: 4000,
            timerProgressBar: true,
          });
        },
        error: (err) => {
          const mensaje = err.error?.mensaje || 'Error al actualizar la tarea';
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: mensaje,
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 4000,
            timerProgressBar: true,
          });
        },
      });
    } else {
      this.service.create(task).subscribe({
        next: (taskNew) => {
          this.tasks = [...this.tasks, { ...taskNew }];
          Swal.fire({
            toast: true,
            position: 'top-end',
            title: 'Tarea Creada!',
            icon: 'success',
            showConfirmButton: false,
            timer: 4000,
            timerProgressBar: true,
          });
        },
        error: (err) => {
          const mensaje = err.error?.mensaje || 'Error al crear la tarea';
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: mensaje,
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 4000,
            timerProgressBar: true,
          });
        },
      });
    }
  }

  onUpdateTaskEvent(task: Task): void {
    const taskCopy = { ...task };
    delete taskCopy.createdDate;
    this.taskSelected = taskCopy as Task;
  }

  onRemoveTaskEvent(id: number): void {
    Swal.fire({
      title: 'Eliminar Tarea',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.remove(id).subscribe((taskDeleted) => {
          this.tasks = this.tasks.filter((task) => task.id != id);
          Swal.fire({
            toast: true,
            position: 'top-end',
            title: 'Tarea Eliminada!',
            icon: 'info',
            showConfirmButton: false,
            timer: 4000,
            timerProgressBar: true,
          });
        });
      }
    });
  }
}
