export class LoginRequest extends URLSearchParams {
  constructor(email: string, password: string) {
    super();

    this.append('username', email);
    this.append('password', password);
    this.append('grant_type', 'password');
    this.append('client_id', 'client-ui');
  }
}
