export declare type DatasetValue = {
  [key: string]: string;
};

declare type DatasetMap = {
  [key: string]: SelectDataset;
};

export class SelectDataset {

  readonly values: DatasetValue;

  constructor(values: DatasetValue) {
    this.values = values;
  }

  public keys(): string[] {
    return Object.keys(this.values);
  }

  public static getDatasetByName(name: string): SelectDataset | undefined {
    return SelectDataset.DATASET_MAP[name];
  }

  private static RACE_CODE: SelectDataset = new SelectDataset({
    "": "Unknown",
    "W": "White",
    "B": "Black or African American",
    "A": "Asian",
    "L": "Latino or Hispanic",
    "I": "American Indian or Alaska Native",
    "H": "Native Hawaiian and Other Pacific Islander",
    "O": "Other"
  });

  private static SEX_CODE: SelectDataset = new SelectDataset({
    "": "Unknown",
    "M": "Male",
    "F": "Female"
  });

  private static STATES: SelectDataset = new SelectDataset({
    "": "Unknown",
    "AL": "Alabama",
    "AK": "Alaska",
    "AZ": "Arizona",
    "AR": "Arkansas",
    "CA": "California",
    "CO": "Colorado",
    "CT": "Connecticut",
    "DE": "Delaware",
    "DC": "District of Columbia",
    "FL": "Florida",
    "GA": "Georgia",
    "HI": "Hawaii",
    "ID": "Idaho",
    "IL": "Illinois",
    "IN": "Indiana",
    "IA": "Iowa",
    "KS": "Kansas",
    "KY": "Kentucky",
    "LA": "Louisiana",
    "ME": "Maine",
    "MD": "Maryland",
    "MA": "Massachusetts",
    "MI": "Michigan",
    "MN": "Minnesota",
    "MS": "Mississippi",
    "MO": "Missouri",
    "MT": "Montana",
    "NE": "Nebraska",
    "NV": "Nevada",
    "NH": "New Hampshire",
    "NJ": "New Jersey",
    "NM": "New Mexico",
    "NY": "New York",
    "NC": "North Carolina",
    "ND": "North Dakota",
    "OH": "Ohio",
    "OK": "Oklahoma",
    "OR": "Oregon",
    "PA": "Pennsylvania",
    "RI": "Rhode Island",
    "SC": "South Carolina",
    "SD": "South Dakota",
    "TN": "Tennessee",
    "TX": "Texas",
    "UT": "Utah",
    "VT": "Vermont",
    "VA": "Virginia",
    "WA": "Washington",
    "WV": "West Virginia",
    "WI": "Wisconsin",
    "WY": "Wyoming"
  });

  private static DATASET_MAP: DatasetMap = {
    "RaceCode": SelectDataset.RACE_CODE,
    "SexCode": SelectDataset.SEX_CODE,
    "State": SelectDataset.STATES
  };
}
