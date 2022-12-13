export type RuStoreProduct = {
  productId: string;
  productType?: string; // TODO typing enum
  productStatus: string; // TODO typing enum
  productLabel?: string;
  price?: number;
  currency?: string;
  language?: string;
  title?: string;
  description?: string;
  imageUrl?: string;
  promoImageUrl?: string;
  subscription?: {
    subscriptionPeriod?: string; // TODO typing enum
    freeTrialPeriod?: string; // TODO typing enum
    gracePeriod?: string; // TODO typing enum
    introductoryPrice?: string; // TODO typing enum
    introductoryPriceAmount?: string; // TODO typing enum
    introductoryPricePeriod?: {
      years: number;
      months: number;
      days: number;
    };
  };
};

export type RuStoreError = {
  name?: string;
  code?: string;
  description?: string;
};

export type RuStoreNativeModule = {
  getProducts: (
    productIds: string[],
    onSuccess: (products: RuStoreProduct[]) => any,
    onError?: (error: RuStoreError) => any
  ) => void;
};
