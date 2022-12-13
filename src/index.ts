// import { NativeModules, Platform } from 'react-native';
import { NativeModules } from 'react-native';
import type { RuStoreNativeModule } from './types';

// const LINKING_ERROR =
//   `The package 'react-native-rustore-purchase' doesn't seem to be linked. Make sure: \n\n` +
//   Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
//   '- You rebuilt the app after installing the package\n' +
//   '- You are not using Expo Go\n';

// const RustorePurchase = NativeModules.RustorePurchase
//   ? (NativeModules.RustorePurchase as RuStoreNativeModule)
//   : new Proxy(
//       {},
//       {
//         get() {
//           throw new Error(LINKING_ERROR);
//         },
//       }
//     );

const RustorePurchase = NativeModules.RustorePurchase as RuStoreNativeModule;

const RuStorePurchaseModule = {
  getProducts: ({ productIds }: { language: string; productIds: string[] }) => {
    return new Promise<any>((resolve, reject) => {
      if (RustorePurchase.getProducts) {
        RustorePurchase.getProducts(
          productIds,
          (products) => resolve(products),
          (error) => reject(error)
        );
      }
    });
  },
};
export default RuStorePurchaseModule;
