import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Task } from '../../models/task';

@Component({
  selector: 'app-tasks',
  imports: [],
  templateUrl: './tasks.component.html',
})
export class TasksComponent {
  @Input() tasks: Task[] = [];

  title = 'Task List';

  @Output() updateTaskEvent = new EventEmitter();

  @Output() removeTaskEvent = new EventEmitter();

  @Output() filterById = new EventEmitter();

  onUpdateTask(task: Task): void {
    this.updateTaskEvent.emit(task);
  }

  onRemoveTask(id: number): void {
    this.removeTaskEvent.emit(id);
  }

  onFilterById(event: Event): void {
    const value = (event.target as HTMLInputElement).value;
    console.log('Emitiendo ID al padre:', value);
    this.filterById.emit(value);
  }
}
