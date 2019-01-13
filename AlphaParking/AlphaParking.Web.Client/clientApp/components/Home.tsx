import * as React from 'react';
import { Jumbotron } from 'react-bootstrap';

import LinkToAdminWebApp from './common/LinkToAdminWebApp';
import LinkToLogin from './common/LinkToLogin';
import LinkToRegistration from './common/LinkToRegistration';
import './Home.scss';

const Home: React.SFC = () => {
    return (<Jumbotron>
        <h1 className={'alpha-brand'}>Парковка компании <span >"Альфа"</span></h1>
        <p>Данный сайт предоставляет следующие возможности для сотрудников компании:</p>
        <div><p>- получение парковочного места для автомобиля;</p></div>
        <div><p>- ежедневная парковка автомобиля на одном из имеющихся парковочных мест;</p></div>
        <div><p>- регистрация автомобиля в базе данных компании;</p></div>
        <div><p>- делигация возможности парковки другому сотруднику на время отпуска;</p></div>
        <div><p>- редактирование пользовательских данных.</p></div>
        <p>
            Для дальнейших действий произведите <LinkToLogin text={'вход в систему'} /> или <LinkToRegistration text={'зарегистрируйтесь'} />
        </p>
        <p>
            Если вы являетесь менеджером компании, пройдите <LinkToAdminWebApp text={'на страницу администрирования'} />.
        </p>
    </Jumbotron>);
};

export default Home;