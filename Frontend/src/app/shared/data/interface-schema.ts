import {ValidatorFn} from "@angular/forms";
import Validation from "../../utils/validation";

export class InterfaceSchema {
  transaction: Transaction = new Transaction();
}

export class Transaction {
  name: string = '';
  version: number | undefined;
  fields: Field[] = [];
  combinations: FieldCombination[] = [];
}

export class Field {
  name: string = '';
  maxLength: number | undefined;
  type: FieldType | undefined;
  description: string | undefined;
  components: FieldComponent[] | undefined;
}

export class FieldComponent {
  name: string = '';
  type: FieldType | undefined;
}

export enum FieldType {
  Alphanumeric = "Alphanumeric", Alphabetic = "Alphabetic", Numeric = "Numeric", Name = "Name", Date = "Date"
}

export class FieldCombination {
  keyReference: string | undefined;
  primaryFieldReference: string | undefined;
  requirements: {fields: {mandatoryFields: string[], optionalFields: string[]}} | undefined;
}
