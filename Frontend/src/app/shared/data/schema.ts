export class Schema {
  constructor(schemaFile: SchemaFile) {
    this.file = schemaFile;
  }

  readonly file: SchemaFile;

  content: string | undefined = undefined;
  data: any = undefined;
}

export class SchemaFile {
  constructor(name: string, selected: boolean, uploadedAt: Date) {
    this.name = name;
    this.selected = selected;
    this.uploadedAt = uploadedAt;
  }

  readonly name: string;
  readonly uploadedAt: Date;
  selected: boolean;

  getFormattedDate(): string {
    return this.formatNumber(this.uploadedAt.getDate()) + "." +
      this.formatNumber(1 + this.uploadedAt.getMonth()) + "." +
      this.uploadedAt.getFullYear() + " " +
      this.formatNumber(this.uploadedAt.getHours()) + ":" +
      this.formatNumber(this.uploadedAt.getMinutes());
  }

  private formatNumber(x: number): string {
    return x < 10 ? "0" + x : String(x);
  }
}
