import * as React from 'react';
import { Button, FormControl } from 'react-bootstrap';
import { connect } from 'react-redux';
import { ThunkDispatch } from 'redux-thunk';

import { registrAction } from '../../../ducks/user';
import User from '../../../models/User';
import FieldGroup from '../../common/FieldGroup';

interface IRegistrationFormState {
    id: number;
    login: string;
    password: string;
    fio: string;
    address: string;
    phone: string;
    email: string;
    error: string;
}

interface IMapDispatchToProps {
    registration: (user: User) => void;
}

class RegistrationForm extends React.Component<IMapDispatchToProps, IRegistrationFormState> {
    constructor(props: any) {
        super(props);
        this.state = {
            address: '',
            email: '',
            error: '',
            fio: '',
            id: 0,
            login: '',
            password: '',
            phone: ''
        };
    }

    // TODO: добавить валидаторы параметром
    textHandler = (event: React.FormEvent<FormControl>) => {
        const { error } = this.state;
        const { value, id } = (event.target as HTMLInputElement);
        if (value.length < 4)
            this.setState({
                error: 'Длина должна быть >= 4'
            });
        else
            if (error)
                this.setState({ error: '' });
        this.setState({
            [id]: value
        } as any);
    }

    passwordHandler = (event: React.FormEvent<FormControl>) => {
        const { error } = this.state;
        const english = /^[A-Za-z0-9]*$/;
        const value = (event.target as HTMLInputElement).value;
        if (!english.test(value))
            this.setState({
                error: 'Пароль должен состоять из английский букв и цифр [A-Za-z0-9]'
            });
        else
            if (error)
                this.setState({ error: '' });
        if (value.length < 4)
            this.setState({
                error: 'Длина пароля должна быть >= 4'
            });
        else
            if (error)
                this.setState({ error: '' });
        this.setState({
            password: value
        });
    }

    registrationBtnClickHandler = (event: React.MouseEvent<Button>) => {
        const {
            address,
            email,
            fio,
            id,
            login,
            password,
            phone } = this.state;
        const { registration: registRequest } = this.props;
        const user = new User(id, login, password, fio, address, phone, email);
        registRequest(user);
    }

    render() {
        const { login, password, error } = this.state;
        return <div className='main'>
            <FieldGroup
                id='formControlsText'
                type='text'
                value={login}
                label='Логин'
                onChange={this.textHandler}
                placeholder='Например, ivanov95'
            />
            <FieldGroup
                id='formControlsPassword'
                label='Пароль'
                value={password}
                onChange={this.passwordHandler}
                type='password'
                placeholder='Например, ivanovPass'
            />
            <FieldGroup
                id='formControlsText'
                type='text'
                value={login}
                label='ФИО'
                onChange={this.textHandler}
            />
            <FieldGroup
                id='formControlsText'
                type='text'
                value={login}
                label='Email'
                onChange={this.textHandler}
            />
            <FieldGroup
                id='formControlsText'
                type='text'
                value={login}
                label='Телефон'
                onChange={this.textHandler}
            />
            <FieldGroup
                id='formControlsText'
                type='text'
                value={login}
                label='Адрес'
                onChange={this.textHandler}
            />
            <Button bsStyle='success' bsSize='large' onClick={this.registrationBtnClickHandler}>Зарегистрироваться</Button>
            {error && <div style={{ color: 'red' }}>{error}</div>}
        </div>;
    }
}

const mapDispatchToProps = (dispatch: ThunkDispatch<{}, {}, any>): IMapDispatchToProps => {
    return ({
        registration: (user) => dispatch(registrAction(user))
    });
};

export default connect<{}, IMapDispatchToProps>(null, mapDispatchToProps)(RegistrationForm);