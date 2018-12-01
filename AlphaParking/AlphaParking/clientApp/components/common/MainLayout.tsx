import * as React from 'react';
import consts from '../../constants';
import '../../stylesheets/common.scss';

interface ICar {
    brand: string;
    number: string;
    mark: string;
    userId?: string;
}

interface IState {
    cars: ICar[];
}

class HelloComponent extends React.Component<{}, IState> {

    constructor(props: {}) {
        super(props);
        this.state = {
            cars: []
        };
    }

    componentDidMount() {
        const conf: RequestInit = {
            method: 'GET',
            mode: 'cors'
        };

        fetch(`${consts.SERVER_API}/car`, conf)
            .then((response) => response.ok && response.json())
            .then((result) =>
                this.setState({
                    cars: result
                }))
            .catch((err) => err || 'Пиздец на сервере \_(O_o)_/');
    }

    render() {
        return <div className='main'>
            Полученные машины:
            {this.state.cars.map((elem) =>
                <span style={{ color: '#0000FF', display: 'block' }}> {
                    Object.entries(elem).reduce((accum: string, currVal) => accum + currVal + ' ', '')}
                </span>
            )}</div>;
    }
}

export default HelloComponent;