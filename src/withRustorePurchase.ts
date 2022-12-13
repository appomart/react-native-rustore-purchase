import {
  ConfigPlugin,
  createRunOncePlugin,
  withAndroidManifest,
  AndroidConfig,
} from '@expo/config-plugins';
// @ts-ignore
import { ExpoConfig } from 'expo/config';
// @ts-ignore
import pkg from 'react-native-rustore-purchase/package.json';

const { addMetaDataItemToMainApplication, getMainApplicationOrThrow } =
  AndroidConfig.Manifest;

export type Props = {
  // applicationId: string;
  consoleApplicationId: string;
  deeplinkPrefix?: string;
};

async function setCustomConfigAsync(
  _: Pick<ExpoConfig, 'android'>,
  androidManifest: AndroidConfig.Manifest.AndroidManifest,
  props: Props
): Promise<AndroidConfig.Manifest.AndroidManifest> {
  const mainApplication = getMainApplicationOrThrow(androidManifest);

  addMetaDataItemToMainApplication(
    mainApplication,
    'consoleApplicationId',
    props.consoleApplicationId
  );

  if (props.deeplinkPrefix) {
    addMetaDataItemToMainApplication(
      mainApplication,
      'deeplinkPrefix',
      props.deeplinkPrefix
    );
  }
  return androidManifest;
}

export const withRustorePurchase: ConfigPlugin<Props> = (config, props) => {
  return withAndroidManifest(config, async (v) => {
    // Modifiers can be async, but try to keep them fast.
    v.modResults = await setCustomConfigAsync(v, v.modResults, props);
    return v;
  });
};

export default createRunOncePlugin(withRustorePurchase, pkg.name, pkg.version);
