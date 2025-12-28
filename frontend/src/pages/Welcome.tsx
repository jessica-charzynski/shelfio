import { Button } from "@/components/ui/button";
import shelfioLogo from "@/assets/shelfio-logo.png";

interface WelcomeProps {
  onGetStarted: () => void;
}

const Welcome = ({ onGetStarted }: WelcomeProps) => {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-background px-4">
      <div className="text-center max-w-md mx-auto animate-fade-in">
        {/* Logo */}
        <div className="mb-8">
          <img
            src={shelfioLogo}
            alt="Shelfio - Your Digital Library"
            className="h-60 w-auto mx-auto animate-float"
          />
        </div>

        {/* Tagline */}
        <p className="text-lg text-muted-foreground mb-8">
          Digitalisiere dein physisches Bücherregal
        </p>

        {/* Features List */}
        <ul className="text-left text-muted-foreground space-y-2 mb-10 max-w-xs mx-auto">
          <li className="flex items-center gap-2">
            <span className="text-accent">•</span>
            Behalte deinen Lesefortschritt im Blick
          </li>
          <li className="flex items-center gap-2">
            <span className="text-accent">•</span>
            Organisiere Bücher in Sammlungen
          </li>
          <li className="flex items-center gap-2">
            <span className="text-accent">•</span>
            Bücher bewerten
          </li>
          <li className="flex items-center gap-2">
            <span className="text-accent">•</span>
             Statistiken einsehen
          </li>
        </ul>

        {/* CTA Button */}
        <Button 
          onClick={onGetStarted} 
          size="lg" 
          className="px-8 py-6 text-lg font-semibold"
        >
          Los geht’s
        </Button>
      </div>
    </div>
  );
};

export default Welcome;
