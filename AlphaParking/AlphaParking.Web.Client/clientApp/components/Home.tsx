import * as React from 'react';
import { Jumbotron } from 'react-bootstrap';

import LinkToAdminWebApp from './common/LinkToAdminWebApp';
import LinkToLogin from './common/LinkToLogin';
import LinkToRegistration from './common/LinkToRegistration';
import './Home.scss';

const Home: React.SFC = () => {
    return <Jumbotron>
        <h1>Добро пожаловать на сайт парковки компании <span className={'alpha-brand'}>"Альфа"</span>!</h1>
        <p>
            Данный сайт предоставляет следующие возможности для сотрудников компании:
            - получение парковочного места для автомобиля;
            - ежедневная парковка автомобиля на одном из имеющихся парковочных мест;
            - регистрация автомобиля в базе данных компании;
            - делигация возможности парковки другому сотруднику на время отпуска;
            - редактирование пользовательских данных.
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