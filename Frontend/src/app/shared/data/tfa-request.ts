export class TfaRequest extends URLSearchParams {
  constructor(code: string, token: string) {
    super();

    this.append('tfa_token', token);
    this.append('tfa_code', code);
    this.append('grant_type', 'tfa');
    this.append('client_id', 'client-ui');
  }
}
