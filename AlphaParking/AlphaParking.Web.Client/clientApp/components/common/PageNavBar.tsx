import * as React from 'react';
import { Nav, Navbar, NavItem } from 'react-bootstrap';
import { connect } from 'react-redux';
import { RouteComponentProps, withRouter } from 'react-router';
import { ThunkDispatch } from 'redux-thunk';

import { logoutAction } from '../../ducks/user';

// tslint:disable-next-line:no-empty-interface
interface IProps extends RouteComponentProps {
    isAuth: boolean;
}

interface IMapDispatchToProps {
    logout: () => void;
}

const PageNavBar: React.SFC<IProps & IMapDispatchToProps> = (props) => {
    const {
        history: { push },
        logout,
        isAuth
    } = props;

    const brandClick = (event: React.MouseEvent<HTMLAnchorElement>) => {
        event.preventDefault();
        isAuth ? push('/cars') : push('/');
    };

    const carsClick = (event: React.MouseEvent<NavItem>) => {
        push('/cars');
    };

    const parkingPlacesClick = (event: React.MouseEvent<NavItem>) => {
        push('/parkPlaces');
    };

    const profileClick = (event: React.MouseEvent<NavItem>) => {
        push('/profile');
    };

    const loginClick = (event: React.MouseEvent<NavItem>) => {
        push('/login');
    };

    const logoutClick = (event: React.MouseEvent<NavItem>) => {
        logout();
        push('/');
    };

    const authMenu = <>
        <Nav>
            <NavItem eventKey={1} onClick={parkingPlacesClick}>
                Парковочные места
            </NavItem>
            <NavItem eventKey={2} onClick={carsClick}>
                Автомобили
            </NavItem>
        </Nav>
        <Nav pullRight>
            <NavItem eventKey={1} onClick={profileClick}>
                Профиль
            </NavItem>
            <NavItem eventKey={2} onClick={logoutClick}>
                Выйти
            </NavItem>
        </Nav>
    </>;
    const notAuthMenu = <>
        <Nav pullRight>
            <NavItem eventKey={1} onClick={loginClick}>
                Войти
            </NavItem>
        </Nav>
    </>;

    return (<>
        <Navbar>
            <Navbar.Header>
                <Navbar.Brand>
                    <a onClick={brandClick}>Парковка "Альфа"</a>
                </Navbar.Brand>
            </Navbar.Header>
            {isAuth ? authMenu : notAuthMenu}
        </Navbar>
    </>);
};

const mapDispatchToProps = (dispatch: ThunkDispatch<{}, {}, any>): IMapDispatchToProps => {
    return {
        logout: () => dispatch(logoutAction())
    };
};

export default withRouter(connect<{}, IMapDispatchToProps>(null, mapDispatchToProps)(PageNavBar));