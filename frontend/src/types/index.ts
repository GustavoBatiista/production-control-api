export interface Product {
    id: number;
    code: string;
    name: string;
    price: number;
  }
  
  export interface RawMaterial {
    id: number;
    code: string;
    name: string;
    stockQuantity: number;
  }
  
  export interface ProductMaterial {
    id: number;
    productId: number;
    rawMaterialId: number;
    quantityRequired: number;
  }
  
  export interface ProducibleProduct {
    productId: number;
    productCode: string;
    productName: string;
    price: number;
    maxQuantity: number;
  }