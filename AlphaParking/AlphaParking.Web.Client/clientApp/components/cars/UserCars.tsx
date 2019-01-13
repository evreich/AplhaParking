import * as React from 'react';

import { Button, Table } from 'react-bootstrap';
import { connect } from 'react-redux';
import { Dispatch } from 'redux';
import CarApi from '../../api/Cars';
import Car from '../../models/Car';
import './UserCars.scss';

interface IState {
    cars: Car[];
}

interface IProps {
    dispatch: Dispatch;
}

interface IMapStateToProps {
    userId: number;
}

class UserCars extends React.PureComponent<IProps & IMapStateToProps, IState> {
    constructor(props: any) {
        super(props);
        this.state = {
            cars: []
        };
    }

    componentDidMount() {
        const { userId, dispatch } = this.props;
        CarApi.getUserCars(userId, dispatch)
            .then((cars) => cars && this.setState({
                cars: cars.map((car) => new Car(car.number, car.brand, car.model, car.userId))
            }));
    }

    render() {
        const { cars } = this.state;

        const editBtn = <Button className='btn-edit' bsSize='small'>Изменить</Button>;
        const removeBtn = <Button className='btn-remove' bsSize='small'>Удалить</Button>;

        return <>
            <h2>Список автомобилей</h2>
            <Table striped bordered condensed hover>
                <thead>
                    <tr>
                        <th>Номер</th>
                        <th>Марка</th>
                        <th>Модель</th>
                        <th>Действия</th>
                    </tr>
                </thead>
                <tbody>
                    {!cars.length && <tr><td colSpan={4} className='empty-table-msg'>
                        Данные о машинах отсутствуют</td></tr>}
                    {cars.map((car) => <tr>
                        <td>{car.number}</td>
                        <td>{car.brand}</td>
                        <td>{car.model}</td>
                        <td>{editBtn}{removeBtn}</td>
                    </tr>)}
                </tbody>
            </Table>
        </>;
    }
}

const mapStateToProps = (state: any): IMapStateToProps => ({
    userId: state.user.id
});

export default connect<IMapStateToProps>(mapStateToProps)(UserCars);