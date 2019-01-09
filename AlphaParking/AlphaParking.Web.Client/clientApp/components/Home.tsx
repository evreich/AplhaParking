import * as React from 'react';
import { Jumbotron } from 'react-bootstrap';

import LinkToAdminWebApp from './common/LinkToAdminWebApp';
import LinkToLogin from './common/LinkToLogin';
import LinkToRegistration from './common/LinkToRegistration';
import './Home.scss';

const Home: React.SFC = () => {
    return <Jumbotron>
        <h1 className={'alpha-brand'}>Парковка компании <span >"Альфа"</span>!</h1>
        <p>
            Данный сайт предоставляет следующие возможности для сотрудников компании:
            <div>- получение парковочного места для автомобиля;</div>
            <div>- ежедневная парковка автомобиля на одном из имеющихся парковочных мест;</div>
            <div>- регистрация автомобиля в базе данных компании;</div>
            <div>- делигация возможности парковки другому сотруднику на время отпуска;</div>
            <div>- редактирование пользовательских данных.</div>
        </p>
        <p>
            Для дальнейших действий произведите <LinkToLogin text={'вход в систему'} /> или <LinkToRegistration text={'зарегистрируйтесь'} />
        </p>
        <p>
            Если вы являетесь менеджером компании, пройдите <LinkToAdminWebApp text={'на страницу администрирования'} />.
        </p>
    </Jumbotron>;
};

export default Home;