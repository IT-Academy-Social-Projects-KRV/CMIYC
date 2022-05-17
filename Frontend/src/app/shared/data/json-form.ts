export class JsonForm {
  name: string = '';
  inputs: JsonFormInput[] = [];
  combinations: ApiCombination[] = [];
}

export class JsonFormInput {
  name: string = '';
  type: FieldType | undefined;
  description: string = '';

  maxLength: number = 0;
  regex: string | undefined;

  min: number | undefined;
  max: number | undefined;

  options: JsonFormInputOption[] = [];
  components: JsonFormInput[] = [];
}

export class JsonFormInputOption {
  text: string = '';
  value: string = '';
}

export enum FieldType {
  text = "text", number = "number", select = "select", object = "object"
}

export class ApiCombination {
  apiName: string = '';
  fields: FieldReference[] = [];
}

export class FieldReference {
  field: string = '';
  required: boolean = true;
}
