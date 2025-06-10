export class Task {
  id!: number;
  title!: string;
  description!: string;
  status!: string;
  createdDate?: Date;

  constructor() {
    this.status = 'PENDING';
  }
}
