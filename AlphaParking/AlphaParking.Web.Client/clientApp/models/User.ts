import Model from './Model';

export default class User extends Model {
    id: number;
    login: string;
    password: string;
    fio: string;
    address: string;
    phone: string;
    email: string;

    constructor(
        id: number,
        login: string,
        password: string,
        fio: string,
        address: string,
        phone: string,
        email: string) {

        super();
        this.id = id;
        this.login = login;
        this.password = password;
        this.fio = fio;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
}