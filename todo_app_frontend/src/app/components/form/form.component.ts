import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Task } from '../../models/task';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-form',
  imports: [FormsModule, CommonModule],
  templateUrl: './form.component.html',
  standalone: true,
})
export class FormComponent {
  @Input() task: Task = new Task();

  @Output() addTaskEvent = new EventEmitter();

  onSubmit(taskForm: NgForm): void {
    if (taskForm.valid) {
      console.log(this.task);
      this.addTaskEvent.emit(this.task);
      this.clean();
    }
  }

  clean(): void {
    this.task = new Task();
  }
}
