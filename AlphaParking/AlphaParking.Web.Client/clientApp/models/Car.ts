import Model from './Model';

export default class Car extends Model {
    number: string;
    brand: string;
    model: string;
    userId: number;

    constructor(numberVal: string, brand: string, model: string, userId: number) {
        super();
        this.number = numberVal;
        this.brand = brand;
        this.model = model;
        this.userId = userId;
    }
}